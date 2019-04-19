

package com.flong.kotlin.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist

/**
 * Jsoup 防止 XSS 攻击 工具类
 */
object Jsoup2XssUtils {


    /**
     * 使用自带的 basicWithImages 白名单
     * 允许的便签有 a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
     * strike,strong,sub,sup,u,ul,img
     * 以及 a 标签的 href,img 标签的 src,align,alt,height,width,title 属性
     */
    private val whitelist: Whitelist = Whitelist.basicWithImages()

    /**
     * 配置过滤化参数 不对代码进行格式化
     */
    private val outputSettings: Document.OutputSettings = Document.OutputSettings().prettyPrint(false)

    init {
        // 富文本编辑时一些样式是使用style来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加 style 属性
        whitelist.addAttributes(":all", "style")
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * 清除
     * @param content 内容
     *
     */
    fun clean(content: String): String {
        var contentStr: String = content
        if (contentStr.isNotBlank()) {
            contentStr = content.trim { it <= ' ' }
        }
        return Jsoup.clean(contentStr, "", whitelist, outputSettings)
    }

    // -------------------------------------------------------------------------------------------------

}
