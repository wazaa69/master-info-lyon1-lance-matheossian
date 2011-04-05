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
        imgIndexMerge.push_back(vector<bool>());

        for(unsigned int j = 0; j < hauteur; j++)
        {
            imgIndexGrow[i].push_back(-1);
            imgIndexMerge[i].push_back(false);

        }

    }

}


Accroissement::~Accroissement()
{
    cvReleaseImage(&img_src);
    cvReleaseImage(&img_seg);
}




void Accroissement::demarrer(vector<Graine> graines)
{
    //Grow
    deposerGraines(graines);
    contaminationPixelsVoisins();


//    afficherDetails(); //seulement pour les commentaires

    //Merge
//    sePositionnerSurlesGraine(graines);
//    contaminationRegionsVoisines();
//    modificationImgSegEtImgIndexGrow();
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


void Accroissement::sePositionnerSurlesGraine(std::vector<Graine> graines)
{
    for(unsigned int i = 0; i < graines.size(); i++)
    {
        CvPoint g = graines[i].getPtStart();
        imgIndexMerge[g.x][g.y] = true;
        listePointsVoisins.push(cvPoint(g.x, g.y)); //ajouts des graines dans la liste des voisins
    }
}




void Accroissement::contaminationPixelsVoisins()
{

    //de cette façon, on fait grandire chacune des régions autour d'une graine
    while (!listePointsVoisins.empty())
    {
        CvPoint pointCentral= listePointsVoisins.front();
        listePointsVoisins.pop();
        const unsigned int x = pointCentral.x;
        const unsigned int y = pointCentral.y;
        Region region = listeIndexRegions[imgIndexGrow[x][y]]; //avec imgIndexGrow[x][y] >= 0

        if(x > 0 && x < img_src->width-2 && y > 0 && y < img_src->height-2)
        {
            contaminationPixel(cvPoint(x-1, y-1),region);//if(imgIndexGrow[x-1][y-1]   == -1)
            contaminationPixel(cvPoint(x, y-1),region);//if(imgIndexGrow[x][y-1]     == -1)
            contaminationPixel(cvPoint(x+1, y-1),region);//if(imgIndexGrow[x+1][y-1]   == -1) E
            contaminationPixel(cvPoint(x+1, y),region);//if(imgIndexGrow[x+1][y]     == -1)
            contaminationPixel(cvPoint(x+1, y+1),region);//if(imgIndexGrow[x+1][y+1]   == -1)
            contaminationPixel(cvPoint(x, y+1),region);//if(imgIndexGrow[x][y+1]     == -1)
            contaminationPixel(cvPoint(x-1, y+1),region);//if(imgIndexGrow[x-1][y+1]   == -1)
            contaminationPixel(cvPoint(x-1, y),region);//if(imgIndexGrow[x-1][y]     == -1)
        }
        else if(x == 0 && y == 0)
        {
            contaminationPixel(cvPoint(x+1, y),region);//if(imgIndexGrow[x+1][y]     == -1)
            contaminationPixel(cvPoint(x+1, y+1),region);//if(imgIndexGrow[x+1][y+1]   == -1)
            contaminationPixel(cvPoint(x, y+1),region);//if(imgIndexGrow[x][y+1]     == -1)
        }
        else  if(x > 0 && x < img_src->width-2 && y == 0)
        {
            contaminationPixel(cvPoint(x-1, 0),region);//if(imgIndexGrow[x-1][0]     == -1)
            contaminationPixel(cvPoint(x-1, 1),region);//if(imgIndexGrow[x-1][1]     == -1)
            contaminationPixel(cvPoint(x, 1),region);//if(imgIndexGrow[x][1]       == -1)
            contaminationPixel(cvPoint(x+1, 1),region);//if(imgIndexGrow[x+1][1]     == -1) E
            contaminationPixel(cvPoint(x+1, 0),region);//if(imgIndexGrow[x+1][0]     == -1)
        }
        else if(x == img_src->width-1 && y == 0)
        {
            contaminationPixel(cvPoint(x-1, 0),region);//if(imgIndexGrow[x-1][0]     == -1)
            contaminationPixel(cvPoint(x-1, 1),region);//if(imgIndexGrow[x-1][1]     == -1)
            contaminationPixel(cvPoint(x, 1),region);//if(imgIndexGrow[x][1]       == -1)
        }
        else if(x == img_src->width-1 && y > 0 && y < img_src->height-2)
        {
            contaminationPixel(cvPoint(x, y-1),region);//if(imgIndexGrow[x][y-1]     == -1)
            contaminationPixel(cvPoint(x-1, y-1),region);//if(imgIndexGrow[x-1][y-1]   == -1)
            contaminationPixel(cvPoint(x-1, y),region);//if(imgIndexGrow[x-1][y]     == -1)
            contaminationPixel(cvPoint(x-1, y+1),region);//if(imgIndexGrow[x-1][y+1]   == -1)
            contaminationPixel(cvPoint(x, y+1),region);//if(imgIndexGrow[x][y+1]     == -1)
        }
        else if(x == img_src->width-1 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(x, y-1),region);//if(imgIndexGrow[x][y-1]     == -1)
            contaminationPixel(cvPoint(x-1, y-1),region);//if(imgIndexGrow[x-1][y-1]   == -1)
            contaminationPixel(cvPoint(x-1, y),region);//if(imgIndexGrow[x-1][y]     == -1)
        }
        else if(x > 0 && x < img_src->width-2 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(x-1, y),region);//if(imgIndexGrow[x-1][y]     == -1)
            contaminationPixel(cvPoint(x-1, y-1),region);//if(imgIndexGrow[x-1][y-1]   == -1)
            contaminationPixel(cvPoint(x, y-1),region);//if(imgIndexGrow[x][y-1]     == -1)
            contaminationPixel(cvPoint(x+1, y-1),region);//if(imgIndexGrow[x+1][y-1]   == -1)
            contaminationPixel(cvPoint(x+1, y),region);//if(imgIndexGrow[x+1][y]     == -1)
        }
        else if(x == 0 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(0, y-1),region);//if(imgIndexGrow[0][y-1]     == -1)
            contaminationPixel(cvPoint(1, y-1),region);//if(imgIndexGrow[1][y-1]     == -1)
            contaminationPixel(cvPoint(1, y),region);//if(imgIndexGrow[1][y]       == -1)
        }
        else if(x == 0 && y > 0  && y < img_src->height - 2)
        {
            contaminationPixel(cvPoint(0, y-1),region);//if(imgIndexGrow[0][y-1]     == -1)
            contaminationPixel(cvPoint(1, y-1),region);//  if(imgIndexGrow[1][y-1]     == -1)
            contaminationPixel(cvPoint(1, y),region);// if(imgIndexGrow[1][y]       == -1)
            contaminationPixel(cvPoint(1, y+1),region);//if(imgIndexGrow[1][y+1]     == -1)
            contaminationPixel(cvPoint(0, y+1),region);//if(imgIndexGrow[0][y+1]     == -1)
        }

    }

}

void Accroissement::contaminationPixel(const CvPoint& pt, Region& region)
{
    CvScalar color = cvGet2D(img_src, pt.y, pt.x);
    double coulPixel = (color.val[2]+color.val[1]+color.val[0])/3;
    double moyCoulRegion = region.getCouleurMoyenne().moyenne();

//    cout << "ptx " << pt.x << "  pty " << pt.y << endl;
   int azr =  imgIndexGrow[pt.x][pt.y];
    if((imgIndexGrow[pt.x][pt.y] == -1 ) && (abs(coulPixel - moyCoulRegion) <= seuil)) // extenion de la region
    {
        imgIndexGrow[pt.x][pt.y] = region.getIndexRegion();
        region.setNouvMoyenne(Couleur(color)); //calcul la moyenne entre la couleur de la région et color
        region.incTailleRegion();
        listePointsVoisins.push(pt);
        cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());

    }
    else if((imgIndexGrow[pt.x][pt.y] == -1 ) && (abs(coulPixel - moyCoulRegion) > seuil)) // création d'une nouvelle région
    {
        Region r = Region(Graine(pt), Couleur(color.val[2],color.val[1],color.val[0]));
        listeIndexRegions.push_back(r);
        imgIndexGrow[pt.x][pt.y] = r.getIndexRegion();
        listePointsVoisins.push(pt);
        cout << "Création d'une région, nb de régions: " << r.getNombreRegions() << endl;
        cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());

    }
    else if (imgIndexGrow[pt.x][pt.y] == region.getIndexRegion()) // collision avec la même region
    {
            // do nothing
    }
    else if ((imgIndexGrow[pt.x][pt.y] != -1 ) && (region.getTailleRegion() >= listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getTailleRegion())
                && (abs(coulPixel - moyCoulRegion) <= seuil))
    { // absorption d'une partie d'une autre région

        listeIndexRegions[imgIndexGrow[pt.x][pt.y]].decTailleRegion();
        if(listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getTailleRegion() == 0){
             cout << "Suppression d'une région, nb de régions: " << region.getNombreRegions() << endl;
             region.decNombreRegion();
        }

        imgIndexGrow[pt.x][pt.y] = region.getIndexRegion();
        region.setNouvMoyenne(Couleur(color));
        cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());
        region.incTailleRegion();
        listePointsVoisins.push(pt);
    }
}

//
//void Accroissement::contaminationRegionsVoisines()
//{
//
//    while (!listePointsVoisins.empty())
//    {
//        CvPoint pointCentral= listePointsVoisins.front();
//        listePointsVoisins.pop();
//        const unsigned int x = pointCentral.x;
//        const unsigned int y = pointCentral.y;
//        Region region = listeIndexRegions[imgIndexMerge[x][y]]; //avec imgIndexMerge[x][y] >= 0
//
//        if(x > 0 && x < img_src->width-2 && y > 0 && y < img_src->height-2)
//        {
//            if(!imgIndexMerge[x-1][y-1]   == -1)    contaminationRegion(cvPoint(x-1, y-1),region);
//            if(!imgIndexMerge[x][y-1])    contaminationRegion(cvPoint(x, y-1),region);
//            if(!imgIndexMerge[x+1][y-1]   == -1)    contaminationRegion(cvPoint(x+1, y-1),region);
//            if(!imgIndexMerge[x+1][y])    contaminationRegion(cvPoint(x+1, y),region);
//            if(!imgIndexMerge[x+1][y+1]   == -1)    contaminationRegion(cvPoint(x+1, y+1),region);
//            if(!imgIndexMerge[x][y+1])    contaminationRegion(cvPoint(x, y+1),region);
//            if(!imgIndexMerge[x-1][y+1]   == -1)    contaminationRegion(cvPoint(x-1, y+1),region);
//            if(!imgIndexMerge[x-1][y])    contaminationRegion(cvPoint(x-1, y),region);
//        }
//        else if(x == 0 && y == 0)
//        {
//            if(!imgIndexMerge[x+1][y])    contaminationRegion(cvPoint(x+1, y),region);
//            if(!imgIndexMerge[x+1][y+1]   == -1)    contaminationRegion(cvPoint(x+1, y+1),region);
//            if(!imgIndexMerge[x][y+1])    contaminationRegion(cvPoint(x, y+1),region);
//        }
//        else  if(x > 0 && x < img_src->width-2 && y == 0)
//        {
//            if(!imgIndexMerge[x-1][0])  contaminationRegion(cvPoint(x-1, 0),region);
//            if(!imgIndexMerge[x-1][1])  contaminationRegion(cvPoint(x-1, 1),region);
//            if(!imgIndexMerge[x][1]  )  contaminationRegion(cvPoint(x, 1),region);
//            if(!imgIndexMerge[x+1][1])  contaminationRegion(cvPoint(x+1, 1),region);
//            if(!imgIndexMerge[x+1][0])  contaminationRegion(cvPoint(x+1, 0),region);
//        }
//        else if(x == img_src->width-1 && y == 0)
//        {
//            if(!imgIndexMerge[x-1][0])  contaminationRegion(cvPoint(x-1, 0),region);
//            if(!imgIndexMerge[x-1][1])  contaminationRegion(cvPoint(x-1, 1),region);
//            if(!imgIndexMerge[x][1]  )  contaminationRegion(cvPoint(x, 1),region);
//        }
//        else if(x == img_src->width-1 && y > 0 && y < img_src->height-2)
//        {
//            if(!imgIndexMerge[x][y-1])    contaminationRegion(cvPoint(x, y-1),region);
//            if(!imgIndexMerge[x-1][y-1]   == -1)    contaminationRegion(cvPoint(x-1, y-1),region);
//            if(!imgIndexMerge[x-1][y])    contaminationRegion(cvPoint(x-1, y),region);
//            if(!imgIndexMerge[x-1][y+1]   == -1)    contaminationRegion(cvPoint(x-1, y+1),region);
//            if(!imgIndexMerge[x][y+1])    contaminationRegion(cvPoint(x, y+1),region);
//        }
//        else if(x == img_src->width-1 && y == img_src->height-1)
//        {
//            if(!imgIndexMerge[x][y-1])    contaminationRegion(cvPoint(x, y-1),region);
//            if(!imgIndexMerge[x-1][y-1]   == -1)    contaminationRegion(cvPoint(x-1, y-1),region);
//            if(!imgIndexMerge[x-1][y])    contaminationRegion(cvPoint(x-1, y),region);
//        }
//        else if(x > 0 && x < img_src->width-2 && y == img_src->height-1)
//        {
//            if(!imgIndexMerge[x-1][y])    contaminationRegion(cvPoint(x-1, y),region);
//            if(!imgIndexMerge[x-1][y-1]   == -1)    contaminationRegion(cvPoint(x-1, y-1),region);
//            if(!imgIndexMerge[x][y-1])    contaminationRegion(cvPoint(x, y-1),region);
//            if(!imgIndexMerge[x+1][y-1]   == -1)    contaminationRegion(cvPoint(x+1, y-1),region);
//            if(!imgIndexMerge[x+1][y])    contaminationRegion(cvPoint(x+1, y),region);
//        }
//        else if(x == 0 && y == img_src->height-1)
//        {
//            if(!imgIndexMerge[0][y-1])      contaminationRegion(cvPoint(0, y-1),region);
//            if(!imgIndexMerge[1][y-1])      contaminationRegion(cvPoint(1, y-1),region);
//            if(!imgIndexMerge[1][y]  )      contaminationRegion(cvPoint(1, y),region);
//        }
//        else if(x == 0 && y > 0  && y < img_src->height - 2)
//        {
//            if(!imgIndexMerge[0][y-1])      contaminationRegion(cvPoint(0, y-1),region);
//            if(!imgIndexMerge[1][y-1])      contaminationRegion(cvPoint(1, y-1),region);
//            if(!imgIndexMerge[1][y]  )      contaminationRegion(cvPoint(1, y),region);
//            if(!imgIndexMerge[1][y+1])      contaminationRegion(cvPoint(1, y+1),region);
//            if(!imgIndexMerge[0][y+1])      contaminationRegion(cvPoint(0, y+1),region);
//        }
//    }
//}
//
//
//void Accroissement::contaminationRegion(const CvPoint& ptVoisin, Region& regionCentral)
//{
//
//    int indexRegionVoisine = imgIndexGrow[ptVoisin.x][ptVoisin.y];
//
//    if(regionCentral.getIndexRegion() != indexRegionVoisine)
//    {
//        Region regionVoisine = listeIndexRegions[indexRegionVoisine];
//
//        double moyCoulRegionVoisine = regionVoisine.getCouleurMoyenne().moyenne();
//        double moyCoulRegionCentral = regionCentral.getCouleurMoyenne().moyenne();
//
//        //redirection d'une région vers une autres
//        if(moyCoulRegionVoisine == moyCoulRegionCentral)
//            mapDeRedirections.insert ( pair<int,int>(regionVoisine.getIndexRegion(),regionCentral.getIndexRegion()) );
//
//    }
//
//    imgIndexMerge[ptVoisin.x][ptVoisin.y] = true;
//
//    listePointsVoisins.push(ptVoisin);
//}
//
//
//
//void Accroissement::modificationImgSegEtImgIndexGrow()
//{
//
////     map<int,int>::iterator it;
////     for ( it=mapDeRedirections.begin() ; it != mapDeRedirections.end(); it++ )
////        cout << (*it).first << " => " << (*it).second << endl;
//
//
//    //colonne par colonne
//    for(unsigned int x = 0; x < img_src->width; x++)
//        for(unsigned int y = 0; y < img_src->height; y++)
//        {
//            int indexActu = imgIndexGrow[x][y];
//
//            if(mapDeRedirections.find(indexActu) != mapDeRedirections.end())
//            {
//                int nouvIndex = mapDeRedirections[indexActu];
//                imgIndexGrow[x][y] = nouvIndex;
//                cvSet2D(img_seg, y, x, listeIndexRegions[nouvIndex].getCouleurVisuelle().getCvScalar());
//            }
//        }
//}


const IplImage* Accroissement::getImgSeg() const{return img_seg;}
const IplImage* Accroissement::getImgSrc() const {return img_src;}
//
//void Accroissement::afficherDetails(){
//
//    cout << "Nombre de régions avant fusion : " << listeIndexRegions.size() << endl;
//
//    vector<double> nbMoyennesDiff;
//
//    for(int i = 0; i < listeIndexRegions.size(); i++){
//
//        double moyenne = listeIndexRegions[i].getCouleurMoyenne().moyenne();
//
//        cout << "Moyenne de la Région n°" << i << " : " << moyenne << endl;
//
//        bool in = false;
//        for(int j = 0; j < nbMoyennesDiff.size() && !in; j++) if(nbMoyennesDiff[j] == moyenne) {in = true; break;}
//        if(!in) nbMoyennesDiff.push_back(moyenne);
//    }
//
//    cout << endl << "--------------------------------------------------" << endl;
//
//    for(int i = 0; i < nbMoyennesDiff.size(); i++)
//        cout << "Liste des niveaux de gris : " << nbMoyennesDiff[i] << endl;
//
//    cout << "Après la fusion on obtiendra au minimum " << nbMoyennesDiff.size() << " régions différentes." << endl;
//
//}
