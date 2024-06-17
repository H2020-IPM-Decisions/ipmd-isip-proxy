# IPMD-ISIP-PROXY

This application will serve as a proxy between the IPM Decisions Platform and ISIP, transforming requests and responses
so that the respective software systems will be able to communicate. It provides one endpoint, which enables the
IPM Decisions Platform to trigger the Siggetreide model available at ISIP.

## Technology

The application was built using the following main technologies

- Java 1.8
- SpringBoot 2.7.1
- Apache Maven 3.9.6

The Java and SpringBoot versions can not be updated before the environment in which the application will run is updated. 

## Getting started

Clone the Github repository to get the latest version of the code

```
$ git clone 
```

### Build and test the source code

```
$ ./mvnw clean install 
```

### Run application

The application will by default run on the port `13085` and context path `/ipmd`, and it will write to a log file located at the relative path `logs/ipmd-isip-proxy.log`.

```
$ ./mvnw spring-boot:run
```

If you need to configure these settings, you can start the application like this:

```
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--PORT=13 --CONTEXT_PATH=/alternative --LOG_FILE=logs/another_file.log"
```

### Package application

```
$ ./mvnw clean package
```

Run jar file with default settings:
```
$ java -jar target/ipmd-isip-proxy-1.0.0.jar
```
or configured:
```
$ java -jar target/ipmd-isip-proxy-1.0.0.jar --PORT=13 --CONTEXT_PATH=/alternative --LOG_FILE=logs/another_file.log
```

### Test endpoint locally

Minimal example request to trigger the endpoint, when application is run with default settings. Please note that you will need a token with access to the ISIP API.
The token is forwarded to ISIP by the proxy.

```
curl 'http://localhost:13085/ipmd/siggetreide' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer [TOKEN]' \
--data '{
  "modelId": "PUCCRE",
  "crop": "TRZAW",
  "timeZone": "Europe/Oslo",
  "weatherData": {
  "modelId": "PUCCRE",
  "crop": "TRZAW",
  "timeZone": "Europe/Oslo",
  "weatherData": {
    "timeStart": "2024-02-15T00:00:00Z",
    "timeEnd": "2024-02-15T23:00:00Z",
    "interval": 3600,
    "weatherParameters": [
      2001,
      3001,
      1001
    ],
    "locationWeatherData": [
      {
        "longitude": 7.7934855222702035,
        "latitude": 49.836999863336345,
        "altitude": 0.0,
        "amalgamation": [
          0,
          0,
          0
        ],
        "data": [
       [
          0.0,
          93.46002197265625,
          9.120000839233398
        ],
        [
          0.0,
          92.87644958496094,
          10.020000457763672
        ],
        [
          0.0,
          94.10567474365234,
          9.319999694824219
        ],
        [
          0.0,
          94.39900970458984,
          8.719999313354492
        ],
        [
          0.0,
          95.34876251220703,
          8.219999313354492
        ],
        [
          0.0,
          95.34150695800781,
          8.020000457763672
        ],
        [
          0.0,
          95.32148742675781,
          7.46999979019165
        ],
        [
          0.0,
          94.99288940429688,
          7.420000076293945
        ],
        [
          0.0,
          93.40949249267578,
          8.119999885559082
        ],
        [
          0.0,
          91.59551239013672,
          9.469999313354492
        ],
        [
          0.0,
          88.6478500366211,
          10.819999694824219
        ],
        [
          0.0,
          84.45087432861328,
          12.469999313354492
        ],
        [
          0.0,
          82.3797378540039,
          13.770000457763672
        ],
        [
          0.0,
          78.47408294677734,
          14.520000457763672
        ],
        [
          0.0,
          70.3790054321289,
          15.069999694824219
        ],
        [
          0.0,
          71.71400451660156,
          14.569999694824219
        ],
        [
          0.0,
          78.54264068603516,
          13.219999313354492
        ],
        [
          0.0,
          87.18363189697266,
          11.020000457763672
        ],
        [
          0.0,
          90.67764282226562,
          9.620000839233398
        ],
        [
          0.0,
          90.04525756835938,
          9.370000839233398
        ],
        [
          0.0,
          88.19214630126953,
          9.020000457763672
        ],
        [
          0.0,
          87.30526733398438,
          9.170000076293945
        ],
        [
          0.0,
          85.84396362304688,
          9.370000839233398
        ],
        [
          0.0,
          77.11703491210938,
          10.819999694824219
        ]
        ],
        "width": 3,
        "qc": [
          0,
          0,
          0
        ],
        "length": 24
      }
    ]
  }
}'
```


