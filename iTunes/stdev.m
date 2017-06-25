% % hi = [1,2,3,4,5]
% % freq = [1,2,1,3,1]
% % hilong = [1,2,2,3,4,4,4,5]
% % n = length(hilong)
% % s1 = std(hilong) * sqrt((n-1)/n)
% % m = mean(hilong)
% % s2 = 0;
% % n = 0;
% % for(i = 1: 5)
% %     s2 = s2 + freq(i) * ( hi(i) - m ) ^2;
% %     n = n + freq(i);
% % end
% % n = n
% % s2 = s2 / n; 
% % s2 = sqrt(s2)
% % the above was to test my standard deviation finding code. the following attempts to graph a normal distribution

m = 50;
s = 20;
min = 1;
max = 100;
list = exp( -.5 * (((min:max) - m)/s) .^ 2) ./ (s * sqrt(2 * pi) );
steps = min:max;
plot(steps, list, steps, list .* .5);