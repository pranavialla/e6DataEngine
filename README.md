# Architecture Overview
A distributed OLAP query engine implementing sorting across multiple worker nodes using a coordinator-worker pattern built in Java.

## Components
Driver (Coordinator): Orchestrates distributed sorting using merge-sort strategy
Engine (Worker): Processes data files independently and performs local sorting

## Workflow
Driver distributes CSV files to engine instances via round-robin
Engines sort data locally by (batch_year, university_ranking, batch_ranking)
Driver performs k-way merge of sorted results
Final output written to output.txt


## command to run

go to root (pranavi)
chmod +x run.sh  
./run.sh
