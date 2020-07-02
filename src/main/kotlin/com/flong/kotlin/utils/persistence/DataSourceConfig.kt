package com.flong.kotlin.utils.persistence

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import com.baomidou.mybatisplus.entity.GlobalConfiguration
import com.baomidou.mybatisplus.enums.IdType
import com.baomidou.mybatisplus.mapper.LogicSqlInjector
import com.baomidou.mybatisplus.plugins.PaginationInterceptor
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource
import javax.servlet.Filter
import javax.servlet.Servlet

@Configuration
@MapperScan(basePackages = arrayOf(DataSourceConfig.PACKAGE), sqlSessionFactoryRef = "sessionFactory")
open class DataSourceConfig {
    //静态常量
    companion object {
        //const 关键字用来修饰常量，且只能修饰  val，不能修饰var,  companion object 的名字可以省略，可以使用 Companion来指代
        const val PACKAGE = "com.flong.kotlin.*.mapper";
        const val TYPEALIASESPACKAGE = "com.flong.kotlin.modules.entity"
    }

    final var MAPPERLOCATIONS = "classpath*:mapper/*.xml"

    @Primary
    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    open fun dataSource(): DataSource {
        return DruidDataSource();
    }


    @Bean
    open fun transactionManager(@Qualifier("dataSource") dataSource: DataSource): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    open fun sessionFactory(dataSource: DataSource): SqlSessionFactory {
        //===1.mybatis-plus globalConfig配置
        var cfg = GlobalConfiguration()

        // 字段的驼峰下划线转换
        //cfg.setDbColumnUnderline(true)
        cfg.isDbColumnUnderline = true
        // 全局主键策略
        cfg.setIdType(IdType.AUTO.key)
        // 全局逻辑删除
        cfg.sqlInjector = LogicSqlInjector()
        cfg.logicDeleteValue = "1"
        cfg.logicNotDeleteValue = "0"

        //===2.构造sessionFactory(mybatis-plus)
        var sf = MybatisSqlSessionFactoryBean()
        sf.setDataSource(dataSource)
        sf.setMapperLocations(PathMatchingResourcePatternResolver().getResources(MAPPERLOCATIONS))
        sf.setGlobalConfig(cfg)
        sf.setTypeAliasesPackage(TYPEALIASESPACKAGE)
        // 分页插件
        sf.setPlugins(arrayOf(PaginationInterceptor()))
        //return sf.getObject()
        return sf.`object`
    }


    /**
     * @Description 初始化druid
     * @Author        liangjl
     * @Date        2018年1月17日 下午4:52:05
     * @return 参数
     * @return ServletRegistrationBean 返回类型
     */
    @Bean
    open fun druidServlet(): ServletRegistrationBean<Servlet> {
        var bean: ServletRegistrationBean<Servlet> = ServletRegistrationBean(StatViewServlet(), "/druid/*")
        /** 初始化参数配置，initParams**/
        //登录查看信息的账号密码.
        bean.addInitParameter("loginUsername", "root")
        bean.addInitParameter("loginPassword", "root")
        //IP白名单 (没有配置或者为空，则允许所有访问)
        bean.addInitParameter("allow", "");
        //IP黑名单 (共存时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        bean.addInitParameter("deny", "192.88.88.88")
        //禁用HTML页面上的“Reset All”功能
        bean.addInitParameter("resetEnable", "false")
        return bean
    }


    @Bean
    open fun filterRegistrationBean(): FilterRegistrationBean<Filter> {
        var filterRegistrationBean = FilterRegistrationBean<Filter>()
        filterRegistrationBean.setFilter(WebStatFilter())
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
        return filterRegistrationBean
    }


}