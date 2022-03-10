Notes:
- long time since working with file IO, not fun, full of pain
- would have preferred to work in Kotlin, cuts down on a lot of boilerplate and has functional methods baked in
- jsonl was new to me, made assumptions it was similar to json, conveniently  jackson deserializes it fine
- can set up a shell script to wrap the call I've provided if needed (didn't want to waste too much time)
- actual problem to solve was fun
- interesting discussion about tradeoffs of having the data itself as thin as possible and composing that at runtime,
  vs storing the fully qualified model

## To run (maven cli required)
- ```brew install maven``` (if you dont have it)
- ```mvn clean install```
- ```mvn compile exec:java -Dexec.args="ambientTemp thermostat-data.jsonl.gz 2016-01-01T03:00"```
- General format for reference: mvn compile exec:java -Dexec.args="`<fieldName>` `<filePath>` `<timestamp>`"
# thermostat
