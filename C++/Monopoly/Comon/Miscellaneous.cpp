
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

void split(std::string toBeSplitted, char separator, std::vector<std::string>* row)
{
	std::size_t currentPosition;
    std::size_t previousPosition;

    previousPosition = toBeSplitted.find(separator);

	if(previousPosition != std::string::npos)
    {
        previousPosition = 0;
		while((currentPosition = toBeSplitted.find(separator, previousPosition)) != std::string::npos
            &&(currentPosition < toBeSplitted.length()))
        {
            row->push_back(toBeSplitted.substr(previousPosition, currentPosition - previousPosition));
            previousPosition = currentPosition + 1;
        }

        row->push_back(toBeSplitted.substr(previousPosition, toBeSplitted.length() - previousPosition));

    }
    else
    {
       row->push_back(toBeSplitted);

    }
}