# instaal docker
sudo apt -y update
sudo apt -y install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
sudo apt update
apt-cache policy docker-ce
sudo apt -y install docker-ce
sudo chmod 666 /var/run/docker.sock
docker ps

# install docker-compose
sudo curl -L "http://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version

# key
cd ~/.ssh
ssh-keygen -t rsa -b 4096 -C "you_email@example.com"
ls
cat id_rsa.pub >> ~/.ssh/authorized_keys
cat id_rsa