# camel-k-example-ping-source

## Overview

An simple example to send a ping to telegram using camel k, I deployed it on an openshift cluster.  

Components:
- broker: just a default broker
- trigger: filtering `myData` and defining subscriber, in this case the subscriber is an integration called `telegram-sink`
- TelegramSink.java: receiving events (pings) and send them to a telegram chatbot.

## How to deploy it on a OpenShift cluster

Prerequisites:
- Red Hat Integration - Camel K operator is installed. 
- Camel K cli is installed.
- Red Hat OpenShift Serverless operator is installed.
- `knative-serving` and `knative-eventing` insance is created using serverless operator.
- Using namespace `testk` (if you don't want to change the namespaces in the code)
- A telegram chatbot, you know the chatID and token of it, don't forget the replace them in the `TelegramSink.java`

```
oc apply -f broker.yaml

oc apply -f test-trigger.yaml

kamel run TelegramSink.java --name telegram-sink --dev

```

## How to test it

```
oc run curl --image=curlimages/curl --rm -i --tty --restart=Never --     curl -v "http://broker-ingress.knative-eventing.svc.cluster.local/testk/default"     -X POST     -H "Ce-Id: testing"     -H "Ce-Specversion: 1.0"     -H "Ce-Type: myData"     -H "Ce-Source: manual"     -H "Content-Type: application/json"     -d '{"message": "Hello from manual test"}'
```
You should then see a hello message in telegram.
