javac src/main/java/Job.java -d build/  
jar cvf target/mdcp-word-counter.jar build/  
/usr/lib/android-sdk/build-tools/30.0.3/d8 --release --output target/mdcp-word-counter-dex.jar target/mdcp-word-counter.jar

