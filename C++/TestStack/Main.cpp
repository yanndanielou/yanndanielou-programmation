#include <iostream>

void fonction(int indice);

int main()
{
	fonction(0);

	return 1;
}


void fonction(int indice)
{
	std::cout << indice << std::endl;
	fonction(indice+1);
}