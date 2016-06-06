# metaDataTransformation
This project transforms different kinds of meta data formats into the XML format we used for the data ingestion.

SSOAR: java -cp JAR infolis.metaDataTransformer.ssoar.SSOARTransformator INPUTDIR OUTPUTDIR
ICPSR: java -cp JAR infolis.metaDataTransformer.icpsr.ICPSRTransformer INPUTDIR OUTPUTDIR
Springer: java -cp JAR infolis.metaDataTransformer.springer.SpringerTransformer INPUTDIR METAOUTPUT TEXTOUTPUT

For the data from Springer represented in the A++ format, we can directly extract the meta data as well as the fulltext itself.
