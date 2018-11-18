resource "aws_key_pair" "key_pair" {
  public_key = "${file("key/beerstore_key.pub")}"
}

resource "aws_instance" "instances" {
  count = 3

  ami = "ami-013be31976ca2c322"
  instance_type = "t2.micro"

  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index)}"

  key_name = "${aws_key_pair.key_pair.key_name}"

  tags {
    Name = "hibicode_instances"
  }
}

output "public_ips" {
  value = "${join(", ", aws_instance.instances.*.public_ip)}"
}