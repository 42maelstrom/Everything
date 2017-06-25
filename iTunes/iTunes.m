% December 22, 2013

clc;
clear all;

hi = [1,2,5,10,30,50,100,150,200,250,300];

spread = 150;

data = csvread(['/Users/16alford_simon/git/Everything/iTunes/Old Library Data/Length Spread Data/Songs_Per_Time_1_interval_',num2str(spread),'_spread.csv']);

n = length(data) - 1;
data = data(1:n);
steps = spread / 2 + (1:n);

i = 2;
while(data(1) < data(i) )
    i = i + 1;
end;

i = i - 1;
disp(i);
data = data(1:i);
steps = steps(1:i);

avg = sum(data .* steps) / sum(data)

s = 0;
n = 0;
for(i = 1: 5)
    s = s + data(i) * ( steps(i) - avg ) ^2;
    n = n + data(i);
end
n = n
s = s / n; 
s = sqrt(s)

m = avg;
norm = exp( -.5 * ((steps - m)/s) .^ 2) ./ (s * sqrt(2 * pi);

plot(steps, data, steps, norm);
title(['Songs vs Length (',num2str(spread),' second spread)']);
xlabel('Length (seconds)');
ylabel('Number of songs within spread');
axis([0,700]);
disp('spread = ');
disp(spread);