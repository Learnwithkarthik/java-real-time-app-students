#!/usr/bin/env bash
set -euo pipefail
for tool in java mvn docker curl; do
  if command -v "$tool" >/dev/null 2>&1; then
    echo "[OK] $tool: $(command -v "$tool")"
  else
    echo "[MISSING] $tool"
  fi
done
java -version 2>&1 | head -1 || true
mvn -version 2>/dev/null | head -2 || true
docker compose version 2>/dev/null || true
echo 'Memory:'; free -h | head -2
echo 'Disk:'; df -h . | tail -1
