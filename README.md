# ATG Code Test Question 1

## Assignment

1. Go to ATG - Spel på Sport, Häst och Casino
2. Select Häst
3. Select V4
4. Make a new coupon
5. Mark 4 horses on v4:1, Mark 1 horse on v4:2, Mark 2 horses on v4:3, Mark all horses on v4:4
6. Click “Lägg Spel

**Notes:**

- Make sure that this test can run everyday
- Use Java, Maven

## Solution

The test is found in
[SeleniumWebTest](src/test/java/se/ericthelin/atg/codetest/web/SeleniumWebTest.java).
I have tried to push as much as possible of the technical details into
supporting page objects so that the actual test is readable and using business
language.

I have tried to use id-locators whenever possible and falling back to
'data-test-id'. In one specific case I am using XPath to find an element by text
due the absence of a 'data-test-id' attribute.

You can run the test from an IDE or from the command line. The test assumes that
there is a local Selenium server up and running. There is a [docker-compose.yaml
file](docker-compose.yaml) for this purpose. In order to start the Selenium
server and run the test, you can use the command below:

```
docker-compose up -d
while ! curl -sSL "http://localhost:4444/wd/hub/status" 2>&1 \
        | grep '"ready":' 2>&1 | grep "true" >/dev/null; do
    echo 'Waiting for the Grid'
    sleep 1
done
>&2 echo "Selenium Grid is up - executing tests"
mvn clean test
```

To view the browser window while the test is running you can connect using
remote desktop to `localhost:5900` using password `secret`.

**Note:** The docker-compose.yaml assumes an ARM architecture and has been
tested on an M1 MacBook. If you get errors like the one below, please try [the
Intel specific configuration](docker-compose.intel.yaml) instead.

```
SessionNotCreated Could not start a new session. Response code 500. Message:
Could not start a new session. Could not start a new session. Error while
creating session with the driver service.
```
