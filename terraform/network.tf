resource "aws_vpc" "main" {
  cidr_block = "192.168.0.0/16"

  tags {
    Name = "hibicode"
  }
}

resource "aws_subnet" "private_subnet" {
  count = 3

  vpc_id = "${aws_vpc.main.id}"

  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 10)}"

  availability_zone = "${var.availability_zones[count.index]}"

  tags {
    Name = "hibicode_private_subnet_${count.index}"
  }
}

resource "aws_subnet" "public_subnet" {
  count = 3

  vpc_id = "${aws_vpc.main.id}"

  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 20)}"

  availability_zone = "${var.availability_zones[count.index]}"

  map_public_ip_on_launch = true
  tags {
    Name = "hibicode_public_subnet_${count.index}"
  }
}
