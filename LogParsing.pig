
REGISTER '/home/manas/pigudf.jar'
DEFINE LogParsing LogParsing();
logLine = LOAD '$inputpath'  as (logLine :chararray);
dataTuple = FOREACH logLine GENERATE  FLATTEN (LogParsing(logLine));
STORE dataTuple INTO 'outPath'  USING PigStorage (','); 
