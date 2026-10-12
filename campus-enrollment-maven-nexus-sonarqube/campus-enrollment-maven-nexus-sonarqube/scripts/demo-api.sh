#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8080}"
echo '--- Health ---'
curl --fail --silent --show-error "$BASE_URL/actuator/health"; echo
echo '--- All students ---'
curl --fail --silent --show-error "$BASE_URL/api/students"; echo
echo '--- All courses ---'
curl --fail --silent --show-error "$BASE_URL/api/courses"; echo
echo '--- All enrollments ---'
curl --fail --silent --show-error "$BASE_URL/api/enrollments"; echo
