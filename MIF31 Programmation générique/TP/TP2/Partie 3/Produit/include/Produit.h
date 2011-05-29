#ifndef PRODUIT_H
#define PRODUIT_H


class Produit
{
    public:
        Produit();
        virtual ~Produit();

        Produit & operator=(const Produit & p);
    protected:

    float prix;

    private:
};

#endif // PRODUIT_H
