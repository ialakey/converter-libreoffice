package com.alakey.converterfiles.service


import com.sun.star.beans.PropertyValue
import com.sun.star.comp.helper.Bootstrap
import com.sun.star.frame.XComponentLoader
import com.sun.star.frame.XStorable
import com.sun.star.lang.XComponent
import com.sun.star.uno.Exception
import com.sun.star.uno.UnoRuntime
import com.sun.star.uno.XComponentContext
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Docx to HTML data converter using the LibreOffice library.
 */
class DocxToHtmlConverter {

    val log = LoggerFactory.getLogger(javaClass)

    public fun docxToHtml(file: File, directory: String): String {
        var htmlString: String = ""
        try {
            val context: XComponentContext = Bootstrap.bootstrap()
            val loader: XComponentLoader = UnoRuntime.queryInterface(XComponentLoader::class.java,
                context.getServiceManager().createInstanceWithContext("com.sun.star.frame.Desktop", context)) as XComponentLoader

            val document: XComponent = loader.loadComponentFromURL(
                file.toURI().toString(),
                "_blank", 0,
                emptyArray<PropertyValue>()
            )

            val storable: XStorable = UnoRuntime.queryInterface(XStorable::class.java, document) as XStorable

            val conversionProperties = arrayOf(
                PropertyValue().apply {
                    Name = "FilterName"
                    Value = "HTML (StarWriter)"
                },
                PropertyValue().apply {
                    Name = "Overwrite"
                    Value = true
                }
            )

            val outputHtmlFile = File("$directory/output/${file.name}.html")
            if (outputHtmlFile.exists()) {
                outputHtmlFile.delete()
            }
            storable.storeAsURL(outputHtmlFile.toURI().toString(), conversionProperties)
            document.dispose()
            val encodedHtml = Files.readAllBytes(Paths.get(outputHtmlFile.path))
            htmlString = String(encodedHtml, StandardCharsets.UTF_8)

        } catch (e: Exception) {
            throw IllegalArgumentException("Error while converting docx to html")
        }
        return htmlString
    }
}

data class FileJson(val byteArray: ByteArray, val fileName: String)