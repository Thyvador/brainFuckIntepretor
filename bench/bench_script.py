import subprocess
import os
import re
from _stat import FILE_ATTRIBUTE_ARCHIVE

FILE_NAME = "bench.bf"
JAR_NAME = "bfck-nl.jar"
TEST_ITER = 5
MAX_RANGE = 255

def bench(bf_file, stats_file):
    java_out =java_out = subprocess.check_output("java -jar "+JAR_NAME+" -p "+FILE_NAME, shell=True)
    #os.system("java -jar bfck.jar -p bench.bf")
    #print("out"+os.system("java -jar bfck.jar -p bench.bf"))
    line = java_out.decode('UTF-8')
    m = re.search(r".*EXEC_TIME : (\d*) ms.*EXEC_MOVE : (\d*)", line, re.M | re.S)
    return [int(m.group(1)), m.group(2)]

def init_stats(f):
    f.write("n; Temps d'exécution (ms); Mouvements du pointeur d'exécution\n")


def write_unexecuted(f, n):
    f.write("[" + "+"*n + "]")

def write_file(f, n):
    write_unexecuted(f, n)

def main():
    print("[INFO] Start benchmarking\n")
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
        first = bench(bf_file, stats_file)
        average_time += first[0]
        #All the others iteration, only the EXEC_TIME matters
        for i in range(0,TEST_ITER-1):
            average_time += bench(bf_file, stats_file)[0]
        average_time /= TEST_ITER;
        stats_file.write('{0};{1};{2}\n'.format(str(n), str(average_time), first[1]))


main()