FROM richnorth/vnc-recorder:latest

RUN apt-get update && apt-get install -y \
  bash

CMD [ /bin/bash ]