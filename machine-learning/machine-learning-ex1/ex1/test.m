X = [2104 5 1 45;
     1416 3 2 40;
     1534 3 2 30
     852 2 1 36];
y = [460;
     232;
     315;
     178];
m = length(y);

[X mu sigma] = featureNormalize(X);
X = [ones(m, 1) X];

alpha = 0.01;
num_iters = 100;
theta = zeros(size(X, 2), 1);
for iter = 1:num_iters
    [theta, J_history] = gradientDescentMulti(X, y, theta, alpha, num_iters);
end

theta
plot(1:numel(J_history), J_history);