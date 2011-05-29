#include <iostream>
#include <fstream>
#include <cstdlib>
#include <ctime>

float random(float min, float max)
{
    float random = ((float) rand()) / (float) RAND_MAX;
    return  min + random * (max - min);
}

int main(void)
{

	int nbNoeuds = 3; //nombre de noeuds
	int largeur = 500; //dimensions de l'arène
	int hauteur = 400; //dimensions de l'arène
	float min = 2; //borne de vitesse min
	float max = 4; //borne de vitesse max
	float temps = 5; //temps de simulation en seconde

	std::ofstream fichier("setdest", std::ios::out | std::ios::trunc);

	if(fichier)
	{
		srand(time(NULL));

		for(unsigned int i=0; i < nbNoeuds; ++i )
		{
			int destX = rand() % largeur;
			int DestY = rand() % hauteur;
			float V = random(min, max);
			float T = random(0.0f, temps);

			//de la forme : $ns at T "$node_(i) setdest destX DestY V"
			fichier << "$ns at " << T << " \"$node_(" << i << ") setdest " << destX << " " << DestY << " " << V << "\"" << std::endl;
		}

		fichier.close();
	}

    return 0;
}
