

document.getElementById("combine").addEventListener('click', () => {

   
    const word1 = document.getElementById("word1").value.trim();    
    const word2 = document.getElementById("word2").value.trim();    
    const word3 = document.getElementById("word3").value.trim();    
    const resultText = document.getElementById("result"); 

    resultText.value = "";
    
    for (let i = 0; i < word1.length; i++) {

        for (let j = 0; j < word2.length; j++) {

            for (let k = 0; k < word3.length; k++) {
                
                resultText.value += (word1[i] + "-" + word2[j] + "-" + word3[k] + "\n")
                                   
            }
        }

    }
});