# 1. Build
mvn clean package

# 2. Copy JARs
cp source_code/engine_source_code/target/engine.jar .
cp source_code/driver_source_code/target/driver.jar .

# 3. Run
./start_engine.sh 9001 &
./start_engine.sh 9002 &
./start_engine.sh 9003 &
sleep 2
./start_driver.sh 9001 9002 9003

# 4. Check
cat output.txt