var openIntroBtn = document.getElementsByClassName('to-intro')[0],
    closeIntroBtn = document.getElementsByClassName('closebtn')[0],
    introBox = document.getElementsByClassName('intro-wrapper')[0];


// 添加动画
openIntroBtn.onclick = function() {
    introBox.style.display = 'block';
    introBox.classList.remove('fadeOut');
    introBox.classList.remove('animation');
    introBox.classList.add('fadeIn');
    introBox.classList.add('animation');

};

closeIntroBtn.onclick = function() {
    introBox.classList.remove('fadeIn');
    introBox.classList.remove('animation');
    introBox.classList.add('fadeOut');
    introBox.classList.add('animation');

    setTimeout(function() {
        introBox.style.display = 'none';
    }, 800);
};
