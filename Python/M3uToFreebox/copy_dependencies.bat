@RD /S /Q Dependencies
MD Dependencies
echo "" > Dependencies\__init__.py
COPY ..\Common Dependencies\
COPY ..\Logger Dependencies\