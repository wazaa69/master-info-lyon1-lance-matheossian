#include <cmath>
#include <iostream>
#include <fstream>
#include <map>
#include <string>
#include <sstream>
#include <vector>

struct Node
{
       float x;
       float y;
};

std::vector<std::string> Strtok(std::string line, char delim)
{
       std::vector<std::string> result;

       result.push_back(std::string());
       for (unsigned int i = 0; i < line.size(); i++)
       {
               if (line[i] != delim)
                       result[result.size() - 1] += line[i];
               else
                       result.push_back(std::string());
       }

       return result;
}

float Ax(std::map<int, std::vector<Node *> > &nodes, int t, int node)
{
       float result = 0;
       Node *nx = nodes[node][t];
       for (unsigned int i = 0; i < 10; i++)
       {
               Node *n = nodes[i][t];
               float xbxa = n->x - nx->x;
               float ybya = n->y - nx->y;
               result += sqrt(xbxa * xbxa + ybya * ybya);
       }

       return result / 10.0;
}

float Mx(std::map<int, std::vector<Node *> > &nodes, int node)
{
       int T = 10;
       int dt = 1;
       float result = 0;

       for (int i = 0; i < T - dt; i++)
               result += fabs(Ax(nodes, i + dt, node) - Ax(nodes, i, node));

       return result / (T - dt);
}

float Mob(std::map<int, std::vector<Node *> > &nodes)
{
       float result = 0;
       for (unsigned int i = 0; i < 10; i++)
               result += Mx(nodes, i);

       return result / 10.0;
}

int main()
{
       std::ifstream file("record.pos");
       std::map<int, std::vector<Node *> > nodes;

       while (!file.eof())
       {
               std::string str;
               std::getline(file, str);
               if (str == "")
                       continue;

               std::vector<std::string> toks = Strtok(str, ' ');
               Node *n = new Node();

               std::istringstream iss_id(toks[0]);
               int id;
               iss_id >> id;

               std::istringstream iss_x(toks[1]);
               iss_x >> n->x;

               std::istringstream iss_y(toks[2]);
               iss_y >> n->y;

               nodes[id].push_back(n);
       }

       std::cout << "result: " << Mob(nodes) << std::endl;

       file.close();
       return 0;
}
