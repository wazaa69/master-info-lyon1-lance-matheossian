#ifndef EXPRESSION_H
#define EXPRESSION_H


class Expression
{
    public:
        Expression();
        virtual ~Expression();

        virtual int eval() const =0;
        virtual Expression* clone() const=0;

};

#endif // EXPRESSION_H
