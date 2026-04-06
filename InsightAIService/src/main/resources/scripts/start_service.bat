@echo off
echo ======================================
echo   GitHub Issue Analyzer Starting...
echo ======================================

set config_DIR=D:\config\insightAI.properties

java -Xms512m -Xmx1024m -Dspring.config.additional-location=file:%config_DIR%^ -jar "app.jar"
echo Application stopped.
pause