package com.alakey.converterfiles.service

import com.sun.star.beans.PropertyValue
import com.sun.star.comp.helper.Bootstrap
import com.sun.star.frame.XComponentLoader
import com.sun.star.frame.XStorable
import com.sun.star.lang.XComponent
import com.sun.star.uno.UnoRuntime
import com.sun.star.uno.XComponentContext
import java.io.File
/**
 * HTM to Pdf data converter using the LibreOffice library.
 */
class HtmlToPdfConverter {

    public fun htmlToPdf(file: File, directory: String): ByteArray {
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
                    Value = "writer_web_pdf_Export"
                },
                PropertyValue().apply {
                    Name = "Overwrite"
                    Value = true
                },
            )

            val outputHtmlFile = File("$directory/output/${file.name}.pdf")
            if (outputHtmlFile.exists()) {
                outputHtmlFile.delete()
            }
            storable.storeToURL(outputHtmlFile.toURI().toString(), conversionProperties)
            document.dispose()
            return outputHtmlFile.readBytes()
        } catch (e: Exception) {
        throw IllegalArgumentException("Error while converting html to pdf")
        }
    }
}