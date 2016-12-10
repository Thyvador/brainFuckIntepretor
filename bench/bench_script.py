import subprocess
import os
import re

FILE_NAME = "bench.bf" #The file for the benchmark
JAR_NAME = "bfck-nl.jar" #The jar to bench
TEST_ITER = 10
MAX_RANGE = 255

'''
Execute the jar on the specified file

Params:
    bf_file - The file to execute
    
Return:
    An array that contain the execution time in first position and the movement of the execution pointer in second
'''
def bench(bf_file):
    java_out =java_out = subprocess.check_output("java -jar "+JAR_NAME+" -p "+FILE_NAME, shell=True)
    line = java_out.decode('UTF-8')
    m = re.search(r".*EXEC_TIME : (\d*) micro s.*EXEC_MOVE : (\d*)", line, re.M | re.S)
    return [int(m.group(1)), m.group(2)]

'''
Intialize the specified file for statistics

Params:
    f - The file that will contains statistics
    
Return:
    void
'''
def init_stats(f):
    f.write("n; Temps d'exécution (micro s); Mouvements du pointeur d'exécution\n")

'''
Write the specified file for the bench of the "n unexecuted instructions"

Params:
    f - The file that will be use for benchmarking
    n - The current number of iteration
    
Return:
    void
'''
def write_unexecuted(f, n):
    f.write("[" + "+"*1000*n + "]")

    '''
Write the specified file for the bench of the "loop executed n times"

Params:
    f - The file that will be use for benchmarking
    n - The current number of iteration
    
Return:
    void
'''
def write_executed(f, n):
    f.write("+"*n+"[>"+"+-"*100+"<-]")

    '''
Write the specified file for the bench of the "n nested loops"

Params:
    f - The file that will be use for benchmarking
    n - The current number of iteration
    
Return:
    void
'''
def write_nested(f, n):
    ITER_LOOP = "+"*2
    NESTED_LOOP = "[>"+ITER_LOOP
    f.write(ITER_LOOP+NESTED_LOOP*n+"[-]"+"<-]"*n)

'''
Write the specified file for the appropriate bench

Params:
    f - The file that will be use for benchmarking
    n - The current number of iteration
    
Return:
    void
'''
def write_file(f, n):
    write_nested(f, n)

'''
The main function of the script
'''
def main():
    print("[INFO] Start benchmarking...")
    stats_file = open("bench-result.csv", "w")
    init_stats(stats_file);

    for n in range(1,MAX_RANGE):
        print('[INFO] {0:.0f}%'.format(100*n/MAX_RANGE))
        # Writing the file to test
        bf_file = open(FILE_NAME, "w", encoding="utf-8")
        write_file(bf_file, n)
        bf_file.close()

        average_time = 0;
        # First iteration, save the EXEC_MOVE metrics
        first = bench(bf_file)
        average_time += first[0]
        #All the others iteration, only the EXEC_TIME matters
        for i in range(0,TEST_ITER-1):
            average_time += bench(bf_file)[0]
        average_time /= TEST_ITER;
        stats_file.write('{0};{1};{2}\n'.format(str(n), str(average_time).replace(".",","), first[1]))
    print("[INFO] Bench done.")

main()