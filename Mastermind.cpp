#include <iostream>
#include <cstdlib>
#include <ctime>
#include <cctype>
using namespace std;

const char COLORS[] = {'R','G','B','Y','O','P','W','C','L','M'};

void genCode(char code[], int n) {
    for(int i = 0; i < n; i++) {
        code[i] = COLORS[rand() % 10];
    }
}

void getGuess(char guess[], int n) {
    cout << "Enter guess (R, G, B, Y, etc): ";
    for(int i = 0; i < n; i++) {
        cin >> guess[i];
        guess[i] = toupper(static_cast<unsigned char>(guess[i]));
    }
}

void evaluate(const char code[], const char guess[], int n,
            int &correctPos, int &correctColor) {

    correctPos = 0;
    correctColor = 0;

    bool codeUsed[n] = {0};
    bool guessUsed[n] = {0};

    // exact matches
    for(int i = 0; i < n; i++) {
        if(code[i] == guess[i]) {
            correctPos++;
            codeUsed[i] = guessUsed[i] = true;
        }
    }

    // color only matches
    for(int i = 0; i < n; i++) {
        if(guessUsed[i]) continue;

        for(int j = 0; j < n; j++) {
            if(!codeUsed[j] && guess[i] == code[j]) {
                correctColor++;
                codeUsed[j] = true;
                break;
            }
        }
    }
}

int main() {
    srand(time(0));

    int n = 4;
    char code[n];
    char guess[n];

    genCode(code, n);
    cout << "[DEBUG] Secret code: ";
    for(int i = 0; i < n; i++) cout << code[i] << ' ';
    cout << endl;

    int correctPos, correctColor;

    do {
        getGuess(guess, n);
        evaluate(code, guess, n, correctPos, correctColor);

        cout << "Correct Position: " << correctPos << endl;
        cout << "Correct Color Only: " << correctColor << endl;

    } while(correctPos != n);

    cout << "You Win!" << endl;

    return 0;
}