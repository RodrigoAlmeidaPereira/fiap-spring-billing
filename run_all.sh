function build_and_run_rabbit_cluster() {
  cd rabbit-cluster
  bash run.sh
  sleep 3
  cd ..
}

function build_and_run_batch_registration() {
  cd batchregistration
  bash run.sh
  sleep 3
  cd ..
}

function build_and_run_api() {
  cd api
  bash run.sh
  sleep 3
  cd ..
}

function call_add_persons_api() {
  sleep 4
  echo "Calling batch api"
  curl -X POST http://localhost:8081/jobs/persons/invoke
  echo "Batch api called"
}

function call_simulate_transaction_data() {
  echo "Waiting batch execution 90s"
  sleep 30
  echo "Waiting batch execution 60s"
  sleep 30
  echo "Waiting batch execution 30s"
  sleep 30
  echo "Simulating data"
  curl -X POST http://localhost:8080/bills/transactions/simulate-data
}

function finishing() {
  echo "All services are up"
  echo "Access http://localhost:8080/bills/transactions/csv-report.csv to view the transaction report"
  echo "Access http://localhost:8080/bills/persons to view all registered persons"
  echo "Access http://localhost:8080/swagger-ui.html to view Swagger Documentation of the API"
  echo "Access http://localhost:8081/swagger-ui.html to view Swagger Documentation of the Batch API"
}

time (build_and_run_rabbit_cluster)
time (build_and_run_batch_registration)
time (build_and_run_api)
time (call_add_persons_api)
time (call_simulate_transaction_data)
time (finishing)