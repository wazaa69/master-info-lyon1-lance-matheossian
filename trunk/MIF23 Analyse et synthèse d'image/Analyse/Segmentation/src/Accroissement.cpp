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
}


void Accroissement::deposerGraines(vector<Graine> graines)
{
    for(unsigned int i = 0; i < graines.size(); i++)
    {
        CvPoint g = graines[i].getPtStart();

        //création de la région
        CvScalar color = cvGet2D(img_src, g.y, g.x); //y, x et pas l'inverse
        Region region = Region(graines[i], Couleur(color.val[2], color.val[1], color.val[0]));
        region.ajouterPointRegion(g);
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


//int passage = 0;
//passage++;
//cout << passage << endl;

void Accroissement::contaminationPixel(const CvPoint& pt, Region& uneRegion)
{
    Region& region = listeIndexRegions[uneRegion.getIndexRegion()];

    CvScalar color = cvGet2D(img_src, pt.y, pt.x); //couleur du pixel
    double coulPixel = (color.val[2]+color.val[1]+color.val[0])/3; //couleur moyenne du pixel
    double moyCoulRegion = region.getCouleurMoyenne().moyenne(); //couleur moyenne de la région voisinne


    // Extension de la region
    if((imgIndexGrow[pt.x][pt.y] == -1) && (abs(coulPixel - moyCoulRegion) <= seuil) )
    {
        //64565 passages
        imgIndexGrow[pt.x][pt.y] = region.getIndexRegion();
        region.ajouterPointRegion(pt);
        region.setNouvMoyenne(Couleur(color)); //moyenne entre la couleur de la région et celle du pixel
        cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());
        listePointsVoisins.push(pt);
    }
    // Création d'une nouvelle région, on plante une nouvelle gaine
    else if(imgIndexGrow[pt.x][pt.y] == -1 )
    {
        //360 passages
        Region nouvRegion = Region(Graine(pt), Couleur(color.val[2],color.val[1],color.val[0]));
        listeIndexRegions.push_back(nouvRegion);
        imgIndexGrow[pt.x][pt.y] = nouvRegion.getIndexRegion();
        cvSet2D(img_seg, pt.y, pt.x, nouvRegion.getCouleurVisuelle().getCvScalar());
        listePointsVoisins.push(pt);
    }
    //Deux pixels voisins appartiennent une région différente : si leur couleur moyenne respectent le seuil, la plus grande région absorbe la plus petite
    else if (region.getIndexRegion() != imgIndexGrow[pt.x][pt.y] //les deux région sont différentes (cas obligatoire : si on est dans la région)
             && region.getSize() >= listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getSize()  //comparaison de taille
             && abs(listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getCouleurMoyenne().moyenne() - moyCoulRegion) <= seuil //comparaison des couleurs des régions
             && listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getSize() > 0)
    {
        //457 passages --> lourd
        changerProprietaireRegion(region, listeIndexRegions[imgIndexGrow[pt.x][pt.y]], img_seg);
        listePointsVoisins.push(pt);
    }

//        cvWaitKey(1);
//        cvShowImage( "img", getImgSeg() );

}

void Accroissement::afficherInformations()
{
    int compteur = 0;
    for(unsigned int i = 0; i < listeIndexRegions.size(); i++)
        if(listeIndexRegions[i].getListePointsRegion().size()) compteur++;

    cout << "Nombre de regions crees au total: " << Region::getCompteurRegions() << endl;
    cout << "Nombre de regions de la meme couleur : " << compteur << endl;

}

void Accroissement::changerProprietaireRegion(Region& r_grande, Region& r_petite, IplImage* img)
{
    CvPoint p;

    //on calcul la couleur moyenne des deux régions
    r_grande.setNouvMoyenne(r_petite.getCouleurMoyenne());

    for (unsigned int i = 0; i < r_petite.getListePointsRegion().size(); i++)
    {
        p = r_petite.getListePointsRegion()[i];
        r_grande.ajouterPointRegion(p);
        imgIndexGrow[p.x][p.y] = r_grande.getIndexRegion();
        cvSet2D(img, p.y, p.x, r_grande.getCouleurVisuelle().getCvScalar());
    }

    r_petite.getListePointsRegion().clear();
}

const IplImage* Accroissement::getImgSeg() const{return img_seg;}
const IplImage* Accroissement::getImgSrc() const {return img_src;}
