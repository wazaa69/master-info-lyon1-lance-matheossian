#include "../include/Accroissement.h"

using namespace std;


Accroissement::Accroissement(const char* chemin, const double& _seuil): seuil(_seuil)
{

    img_src = cvLoadImage(chemin);
    img_seg = cvCreateImage(cvGetSize(img_src), img_src->depth, 3);

    const unsigned int hauteur = img_src->height;
    const unsigned int largeur = img_src->width;

    cout << hauteur << " * " << largeur << " = " << (hauteur * largeur) <<endl;

    for(unsigned int i = 0; i < largeur; i++)
    {
        imgIndexGrow.push_back(vector<int>());
        for(unsigned int j = 0; j < hauteur; j++) imgIndexGrow[i].push_back(-1);
    }
}

Accroissement::~Accroissement()
{
    cvReleaseImage(&img_src);
    cvReleaseImage(&img_seg);
}

void Accroissement::dispositionAutomatique(vector<Graine> *graines)
{
   unsigned int largeur = getImgSrc()->width;
   unsigned int hauteur = getImgSrc()->height;
   unsigned int pasX = largeur/10;
   unsigned int pasY = hauteur/10;

    for(unsigned int i = 0; i < largeur; i++)
    {
        for(unsigned int j = 0; j < hauteur; j++)
        {
            graines->push_back(Graine(i,j));
            j += pasY;
        }
        i += pasX;
    }

}

void Accroissement::demarrer(vector<Graine> graines)
{
    deposerGraines(graines);
    contaminationPixelsVoisins();
    coloration();
}


void Accroissement::deposerGraines(vector<Graine> graines)
{
    for(unsigned int i = 0; i < graines.size(); i++)
    {
        CvPoint g = graines[i].getPtStart();

        //création de la région
        CvScalar color = cvGet2D(img_src, g.y, g.x); //y, x et pas l'inverse
        Region region = Region(graines[i], Couleur(color.val[2], color.val[1], color.val[0]));
        listeIndexRegions.push_back(region);

        //ajout de l'index et de la couleur sur l'image
        imgIndexGrow[g.x][g.y] = i;
        cvSet2D(img_seg, g.y, g.x, region.getCouleurVisuelle().getCvScalar());
        listePointsVoisins.push(cvPoint(g.x, g.y)); //ajouts des graines dans la liste des voisins
    }
}


//8-connexes
void Accroissement::contaminationPixelsVoisins()
{

    //de cette façon, on fait grandire chacune des régions autour d'une graine
    while (!listePointsVoisins.empty())
    {
        CvPoint pointCentral = listePointsVoisins.front();
        listePointsVoisins.pop();
        const unsigned int x = pointCentral.x;
        const unsigned int y = pointCentral.y;
        Region region = listeIndexRegions[imgIndexGrow[x][y]]; //avec imgIndexGrow[x][y] >= 0

        if(x > 0 && x < img_src->width-2 && y > 0 && y < img_src->height-2)
        {
            contaminationPixel(cvPoint(x-1, y-1),region);
            contaminationPixel(cvPoint(x, y-1),region);
            contaminationPixel(cvPoint(x+1, y-1),region);
            contaminationPixel(cvPoint(x+1, y),region);
            contaminationPixel(cvPoint(x+1, y+1),region);
            contaminationPixel(cvPoint(x, y+1),region);
            contaminationPixel(cvPoint(x-1, y+1),region);
            contaminationPixel(cvPoint(x-1, y),region);
        }
        else if(x == 0 && y == 0)
        {
            contaminationPixel(cvPoint(x+1, y),region);
            contaminationPixel(cvPoint(x+1, y+1),region);
            contaminationPixel(cvPoint(x, y+1),region);
        }
        else  if(x > 0 && x < img_src->width-2 && y == 0)
        {
            contaminationPixel(cvPoint(x-1, 0),region);
            contaminationPixel(cvPoint(x-1, 1),region);
            contaminationPixel(cvPoint(x, 1),region);
            contaminationPixel(cvPoint(x+1, 1),region);
            contaminationPixel(cvPoint(x+1, 0),region);
        }
        else if(x == img_src->width-1 && y == 0)
        {
            contaminationPixel(cvPoint(x-1, 0),region);
            contaminationPixel(cvPoint(x-1, 1),region);
            contaminationPixel(cvPoint(x, 1),region);
        }
        else if(x == img_src->width-1 && y > 0 && y < img_src->height-2)
        {
            contaminationPixel(cvPoint(x, y-1),region);
            contaminationPixel(cvPoint(x-1, y-1),region);
            contaminationPixel(cvPoint(x-1, y),region);
            contaminationPixel(cvPoint(x-1, y+1),region);
            contaminationPixel(cvPoint(x, y+1),region);
        }
        else if(x == img_src->width-1 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(x, y-1),region);
            contaminationPixel(cvPoint(x-1, y-1),region);
            contaminationPixel(cvPoint(x-1, y),region);
        }
        else if(x > 0 && x < img_src->width-2 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(x-1, y),region);
            contaminationPixel(cvPoint(x-1, y-1),region);
            contaminationPixel(cvPoint(x, y-1),region);
            contaminationPixel(cvPoint(x+1, y-1),region);
            contaminationPixel(cvPoint(x+1, y),region);
        }
        else if(x == 0 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(0, y-1),region);
            contaminationPixel(cvPoint(1, y-1),region);
            contaminationPixel(cvPoint(1, y),region);
        }
        else if(x == 0 && y > 0  && y < img_src->height - 2)
        {
            contaminationPixel(cvPoint(0, y-1),region);
            contaminationPixel(cvPoint(1, y-1),region);
            contaminationPixel(cvPoint(1, y),region);
            contaminationPixel(cvPoint(1, y+1),region);
            contaminationPixel(cvPoint(0, y+1),region);
        }

    }
}


int passage = 0;

//cout << passage << endl;

void Accroissement::contaminationPixel(const CvPoint& pt, Region& uneRegion)
{

    //Recherche si il y a une redirection vers une autres région
    Region& region = listeIndexRegions[indexRedirection(uneRegion)];

    CvScalar color = cvGet2D(img_src, pt.y, pt.x); //couleur du pixel
    double coulPixel = (color.val[2]+color.val[1]+color.val[0])/3; //couleur moyenne du pixel
    double moyCoulRegion = region.getCouleurMoyenne().moyenne(); //couleur moyenne de la région voisinne


    // Extension de la region
    if((imgIndexGrow[pt.x][pt.y] == -1) && (abs(coulPixel - moyCoulRegion) <= seuil) )
    {
        imgIndexGrow[pt.x][pt.y] = region.getIndexRegion();
        region.tailleRegion++;
        //region.ajouterPointRegion(pt);
        region.setNouvMoyenne(Couleur(color)); //moyenne entre la couleur de la région et celle du pixel
        cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());
        listePointsVoisins.push(pt);
    }
    // Création d'une nouvelle région, on plante une nouvelle gaine
    else if(imgIndexGrow[pt.x][pt.y] == -1 )
    {
        Region nouvRegion = Region(Graine(pt), Couleur(color.val[2],color.val[1],color.val[0]));
        listeIndexRegions.push_back(nouvRegion);
        imgIndexGrow[pt.x][pt.y] = nouvRegion.getIndexRegion();
        cvSet2D(img_seg, pt.y, pt.x, nouvRegion.getCouleurVisuelle().getCvScalar());
        listePointsVoisins.push(pt);
    }
    //Deux pixels voisins appartiennent une région différente : si leur couleur moyenne respectent le seuil, la plus grande région absorbe la plus petite
    else
    {
        Region& regionDuPoint = listeIndexRegions[indexRedirection(listeIndexRegions[imgIndexGrow[pt.x][pt.y]])];

        if(region.getIndexRegion() != regionDuPoint.getIndexRegion() //les deux région sont différentes (cas obligatoire : si on est dans la région)
             && regionDuPoint.tailleRegion > 0
             && region.tailleRegion >= regionDuPoint.tailleRegion  //comparaison de taille
             && abs(regionDuPoint.getCouleurMoyenne().moyenne() - moyCoulRegion) <= seuil //comparaison des couleurs des régions
          )
        {
            changerProprietaireRegion(region, regionDuPoint, img_seg);
            listePointsVoisins.push(pt);
        }
    }

    passage++;
    //if(passage%75000 == 0){cvWaitKey(15); cvShowImage( "img", getImgSeg() );}

}

void Accroissement::coloration()
{
    for(unsigned x = 0; x < imgIndexGrow.size(); x++)
        for(unsigned y = 0; y < imgIndexGrow[x].size(); y++)
        {
            //on modifie uniquement les pixels des régions redirigés
//            int redirection = indexRedirection(listeIndexRegions[imgIndexGrow[x][y]]);
//            if(redirection != -1) cvSet2D(img_seg, y, x, listeIndexRegions[redirection].getCouleurVisuelle().getCvScalar());
        }
}


int Accroissement::indexRedirection(const Region& uneRegion)
{
    int guide = -1;
    map<int,int>::iterator it = carteRedirections.find(uneRegion.getIndexRegion());
    while(1)
    {
        if(it != carteRedirections.end())
        {
            guide = it->second;
            it++;
            it = carteRedirections.find(guide);
        }
        else break; //sinon on en fait rien et on conserve le "guide"
    }

    if(guide == -1) guide = uneRegion.getIndexRegion();
    else carteRedirections[uneRegion.getIndexRegion()] = guide;  //mise à jour de la map pour éviter de refaire la boucle

    return guide;
}


int nombreRedirections = 0;
int nombrePointsTransferes = 0;

void Accroissement::afficherInformations()
{

    cout << img_seg->height << " * " << img_seg->width << " = " << (img_seg->height * img_seg->width) <<endl;

    cout << "Nombre de regions crees au total: " << Region::getCompteurRegions() << endl;
    cout << "Nombre de regions de la meme couleur : " << Region::getCompteurRegions() - nombreRedirections << endl;

    cout << "Nombre de redirections :" << nombreRedirections << endl;
    cout << "Nombre de points transféré par redirection :" << nombrePointsTransferes << endl;
}



void Accroissement::changerProprietaireRegion(Region& r_grande, Region& r_petite, IplImage* img)
{
    //on calcul la couleur moyenne des deux régions
    r_grande.setNouvMoyenne(r_petite.getCouleurMoyenne());

    carteRedirections.insert(pair<int,int>(r_petite.getIndexRegion(), r_grande.getIndexRegion()));

    r_grande.tailleRegion += r_petite.tailleRegion;
    r_petite.tailleRegion = 0;

    nombreRedirections++; //1 région aglomérée implique une redirection suplpémentaire
}

const IplImage* Accroissement::getImgSeg() const{return img_seg;}
const IplImage* Accroissement::getImgSrc() const {return img_src;}
