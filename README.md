# Body JSON:
```
{
    "byteArray": [1, 2, 3],//File byte array
    "fileName": "input.docx" //Specify the name and permission of the transferred file
}
```

# GET requets:

## convertDocxToHtml - Converting docx to html using LibreOffice

## convertHtmlToPdf - Converting html to pdf using LibreOffice

## Request example:

```
curl --location --request GET 'http://localhost:8080/api/v1/converting/convertDocxToHtml' \
--header 'Content-Type: application/json' \
--data-raw '{
    "byteArray": [1, 2, 3],//File byte array
    "fileName": "input.docx" //Specify the name and permission of the transferred file
}'
```

# POST requests:

## Removing a directory
```
curl --location --request POST 'http://localhost:8080/api/v1/converting/deleteDirectory' \
--data-raw ''
```
