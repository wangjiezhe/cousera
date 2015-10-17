%% Prediction
% Change hidden_layer_size and num_iters and test.

clear; close all;
%clear ; close all; clc

input_layer_size  = 400;
hidden_layer_size = 100;
num_labels = 10;
num_iters = 400;

load('ex4data1.mat');
m = size(X, 1);

%sel = randperm(size(X, 1));
%sel = sel(1:100);
%
%displayData(X(sel, :));
%
%fprintf('Program paused. Press enter to continue.\n');
%pause;


options = optimset('MaxIter', num_iters);

lambda = 1;

costFunction = @(p) nnCostFunction(p, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, X, y, lambda);

initial_Theta1 = randInitializeWeights(input_layer_size, hidden_layer_size);
initial_Theta2 = randInitializeWeights(hidden_layer_size, num_labels);

initial_nn_params = [initial_Theta1(:) ; initial_Theta2(:)];

[nn_params, cost] = fmincg(costFunction, initial_nn_params, options);

Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), ...
                 num_labels, (hidden_layer_size + 1));

%fprintf('Program paused. Press enter to continue.\n');
%pause;


%displayData(Theta1(:, 2:end));
%
%fprintf('\nProgram paused. Press enter to continue.\n');
%pause;


pred = predict(Theta1, Theta2, X);

fprintf('\nTraining Set Accuracy: %f\n', mean(double(pred == y)) * 100)
