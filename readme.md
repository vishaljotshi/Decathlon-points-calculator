# Decathlon Points Calculator

An application to calculate Decathlon points of athletes and generate XML with their scores and position.

Some sample input CSV files are provided to test the application on path :

```
points-calculator/sample-input/sample-input1.csv
```

## Installation and Requirements

Requires Java JDK 8 for compilation and running the application

## Usage

```shell
# For running unit tests
mvn test

# Supported commandline arguments:
--input-file (absolute/relative file path)
--output-file (absolute/relative file path)
--csv-delimiter (Optional, Default value is ';') 

# For compiling and running the application with maven
mvn clean compile exec:java -Dexec.args="--input-file=sample-input/sample-input1.csv --output-file=sample-output.xml"
```
Note: For input/output file names containing spaces ,enclose them in single quotes : '/file path/'

## Input CSV format sample

```csv
Athlete name;12.61;5.00;9.22;1.50;60.39;16.43;21.60;2.60;35.81;5:25.72
```

## Output XML format

```xml
<Athletes>
    <Athlete>
        <name>John Smith</name>
        <points>9990</points>
        <position>1</position>
        <results>
            <eventName>100 meters</eventName>
            <score>10.395</score>
        </results>
        <results>
            <eventName>Long Jump</eventName>
            <score>7.76</score>
        </results>
        <results>
            <eventName>Shot Put</eventName>
            <score>18.40</score>
        </results>
        <results>
            <eventName>High Jump</eventName>
            <score>2.20</score>
        </results>
        <results>
            <eventName>400 Meters</eventName>
            <score>46.17</score>
        </results>
        <results>
            <eventName>100 meters Hurdles</eventName>
            <score>13.80</score>
        </results>
        <results>
            <eventName>Discus Throw</eventName>
            <score>56.17</score>
        </results>
        <results>
            <eventName>Pole Vault</eventName>
            <score>5.28</score>
        </results>
        <results>
            <eventName>Javelin Throw</eventName>
            <score>77.19</score>
        </results>
        <results>
            <eventName>1500 meters</eventName>
            <score>3:53.79</score>
        </results>
    </Athlete>
</Athletes>
```

## Authors
Vishal Jotshi