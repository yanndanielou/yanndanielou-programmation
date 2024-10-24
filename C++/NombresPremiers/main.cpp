#include <cstdlib>
#include <iostream>
#include <math.h>

using namespace std;

int main(int argc, char *argv[])
{
    int nouveauNombre;
    int nombreNombresTrouves = 3;
    
    int *tableau1 = NULL;
    int *tableau2 = NULL;

    
    tableau1 = new int[5000000];
    tableau1[0] = 2;
    tableau1[1] = 3;
    tableau1[2] = 5;
    
    nouveauNombre = 5;
    
    while(nouveauNombre < pow(2,25) -1){
        //std:: cout << nombreNombresTrouves << " nombres premiers trouves" << std::endl;  
        int i;
        
        nouveauNombre +=2;
        
        for(i=0; i<nombreNombresTrouves -1; i++){
            if(nouveauNombre % tableau1[i] == 0)
               break;                 
             }         
         
            if(i == nombreNombresTrouves -1){
       //     std:: cout << "nouveau nombre trouvé: " << nouveauNombre << std::endl; 
            std:: cout << nouveauNombre << endl;
         /*      int *tableau2 = new int[nombreNombresTrouves];
               
               for(int j = 0; j<nombreNombresTrouves-1; j++){
                    tableau2[j] = tableau1[j];
               }
               
               delete[] tableau1;
               tableau1 = new int[nombreNombresTrouves+1];
                
               for(int j = 0; j<nombreNombresTrouves-1; j++){
                    tableau1[j] = tableau2[j];
               }
                  tableau1[nombreNombresTrouves] = nouveauNombre;
                  delete[] tableau2;*/
                  
                  tableau1[nombreNombresTrouves] = nouveauNombre;
                  nombreNombresTrouves++;
             }
             
             else{
                  
         //   std:: cout << "le nombre " << nouveauNombre << " n'est pas premier" << std::endl; 
                  
             }
                  
              
       }      
                               
                             
    
   // system("PAUSE");
    return EXIT_SUCCESS;
}
