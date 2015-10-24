#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

void compileOutPut(vector<char>);
//void executeOutPut(vector<char>);
void interactionMode();

int main(int argc, char *argv[]){

   if(argc != -1){
      string arg = argv[1];
      string stringFile = argv[2];
      vector<char> symbol;

         if (arg == "-c"){ 

            ifstream fin(stringFile);
            if(fin.fail()){
               //Fail to read file
               cout << "Fail" << endl;
            }else{
               //Success read file
               char c;
         
               while(fin >> c){
                  symbol.push_back(c);
               }
               compileOutPut(symbol);
            }
         }
         else if (arg == "-e"){
            ifstream fin(stringFile);
            if(fin.fail()){
               //Fail to read file
               cout << "Fail" << endl;
            }else{
               //Success read file
               char c;
               while(fin >> c){
                  //symbol.push_back(c);

               }
               //executeOutPut(symbol);
            }
         }
   }else{
      //interactione mode
      //interactionMode();
      cout << "Hello" << endl;
   }

   return 0;
}

void compileOutPut(vector<char> symbol){
   
   cout << "#include <iostream>\n" << endl;
   cout << "const int SIZE = 1000;" << endl;
   cout << "int tape[SIZE] = {0};" << endl;
   cout << "int *dp = tape;\n" << endl;
   cout << "int main() {" << endl;

   for(int i = 0; i < symbol.size(); i++)
   {
      switch(symbol[i])
      {
         case '+':
            cout << "\t++*dp;" << endl; 
            break;

         case '-':
            cout << "\t--*dp;" << endl;
            break;
         case '>':
            cout << "\t++dp;" << endl;
            break;
         case '<':
            cout << "\t--dp;" << endl;
            break;
         case ':':
            cout << "\tstd::cout << *dp;" << endl;
            break;
         case '.':
            cout << "\tstd::cout << ((char) *dp);" << endl;
            break;
         case '{':
            cout << "\twhile (*dp) {" << endl;
            break;
         case '}':
            cout << "\t}" << endl;
            break;
         default:
            break;
      }
   }

   cout << "\treturn 0;" << endl;
   cout << "}" << endl;
}

/*
void executeOutPut(vector<char> symbol){

   const int SIZE = 1000;
   int tape[SIZE] = {0};
   int *dp = tape;
   
   for(int i = 0; i < symbol.size(); i++)
   {
      switch(symbol[i])
      {
         case '+':
            cout << "\t++*dp;" << endl; 
            break;

         case '-':
            cout << "\t--*dp;" << endl;
            break;
         case '>':
            cout << "\t++dp;" << endl;
            break;
         case '<':
            cout << "\t--dp;" << endl;
            break;
         case ':':
            cout << "\tstd::cout << *dp;" << endl;
            break;
         case '.':
            cout << "\tstd::cout << ((char) *dp);" << endl;
            break;
         case '{':
            cout << "\twhile (*dp) {" << endl;
            break;
         case '}':
            cout << "\t}" << endl;
            break;
         default:
            break;
      }
   }
}
*/

void interactionMode(){

   cout << "Position: ";
   for(int i = 0; i < 10; i++){
      cout << i << " ";
   }

   cout << "\n";

   cout << "Characters: ";
   for(int i = 0; i < 10; i++){
      cout << i << " ";
   }

   cout << "Integer: ";
   for(int i = 0; i < 10; i++){
      cout << i << " ";
   }

   cout << "Pointer: ";
   for(int i = 0; i < 10; i++){
      cout << i << " ";
   }

}
