del output_pdf_with_watermark.pdf

rem java -jar ..\..\java\ExternalLibraries\pdfbox-app-3.0.1.jar fromtext -pageSize=A4 -standardFont=Times-Roman --input=watermark_text_to_add.txt --output=watermark_only.pdf
rem java -jar ..\..\java\ExternalLibraries\pdfbox-app-3.0.1.jar overlay --input=original_pdf.pdf -default=watermark_only.pdf --output=output_pdf_with_watermark.pdf

rem java -jar ..\..\java\ExternalLibraries\pdfbox-app-2.0.30.jar overlay --input=original_pdf.pdf -default=watermark_only.pdf --output=output_pdf_with_watermark.pdf

java -jar ..\..\java\ExternalLibraries\pdfbox-app-2.0.30.jar OverlayPDF original_pdf.pdf watermark_only.pdf -position background output_pdf_with_watermark.pdf


timeout /t 30
