FROM ubuntu

#version of chrome
ENV CHROME_VERSION 112.0.5615.49

# Workspace
WORKDIR /usr/share/udemy

#installing dependency and updating pre-existing libs
RUN echo "Updating Packages.";apt-get update -y  >>  update.log
RUN echo "Installing Jdk..";apt-get install openjdk-11-jdk -y >> java.log
RUN echo "Installing wget...";apt-get install wget -y >> wget.log
RUN echo "Installing unzip....";apt-get install unzip -y >> unzip.log

#installing Chrome
RUN wget -q http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_${CHROME_VERSION}-1_amd64.deb --no-verbose
RUN apt-get install ./google-chrome-stable_${CHROME_VERSION}-1_amd64.deb -y >> chrome.log

#installing Chromedriver and moving it to /usr/bin
RUN wget https://chromedriver.storage.googleapis.com/${CHROME_VERSION}/chromedriver_linux64.zip --no-verbose
RUN unzip chromedriver_linux64.zip
RUN mv  -v chromedriver /usr/bin

#checking and removing the setup files
RUN ls
RUN rm google-chrome-stable_${CHROME_VERSION}-1_amd64.deb;rm chromedriver_linux64.zip;
RUN rm *.log
RUN echo "removal check";ls
RUN apt-get clean -y

#verifying Versions
HEALTHCHECK CMD java -version;google-chrome --version || exit 1

# ADD .jar under target from host
# into this image
ADD target/selenium-docker.jar 			selenium-docker.jar
ADD target/selenium-docker-tests.jar 	selenium-docker-tests.jar
ADD target/libs							libs
ADD src/test/resources                  src/test/resources

# in case of any other dependency like .csv / .json / .xls
# please ADD that as well

# ADD suite files
#ADD testng.xml				testng.xml

# ADD health check script
#ADD healthcheck.sh                      healthcheck.sh

# BROWSER
# HUB_HOST
# MODULE

ENTRYPOINT java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* io.cucumber.core.cli.Main src/test/resources/features --tags "$TAGS" --glue stepDefinitions