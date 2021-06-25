docker run --tty \
           --network sftp-sink-connector_kafka-connect \
           -v /Users/aqua-len/IdeaProjects/sftp-sink-connector/test-data-star-wars.xml:/test-data-star-wars.xml\
           confluentinc/cp-kafkacat \
           bash -c "
            cat /test-data-star-wars.xml | kafkacat  \
           -b broker:29092 \
            -P -t main"
