

## Workflow
Driver distributes CSV files to engine instances via round-robin
Engines sort data locally by (batch_year, university_ranking, batch_ranking)
Driver performs k-way merge of sorted results
Final output written to output.txt


## command to run

go to root (pranavi)
chmod +x run.sh  
./run.sh
