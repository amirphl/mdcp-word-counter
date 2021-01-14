## mdcp word counter

### how to build jar and dex
- `javac src/main/java/com/example/Job.java -d build/`
- `jar cvf target/mdcp-word-counter.jar build/`
- `/usr/lib/android-sdk/build-tools/30.0.3/d8 target/classes/com/example/Job.class --release --output dex.jar --lib /usr/lib/android-sdk/platforms/android-30/android.jar --classpath target/dependency/*`
or  
- `/usr/lib/android-sdk/build-tools/30.0.3/d8 --release --output dex.jar target/mdcp-word-counter.jar`

### program arguments
- arg 0: input file URL ---> url of a text file
- arg 1: output file path ---> program writes the result in this path in a text file
- arg 2: fraction ---> fraction of input (not yet used)
- arg 3: total fractions (not yet used)
- ex: example.com/input.txt /home/user/output.out 3 10
here 3 10 means we want to use section 3 of 10

### output format
text file
