import matplotlib.pyplot as plt
import numpy as np
from sklearn.metrics import confusion_matrix
import itertools

# Example data (replace with your actual experimental results)
true_labels = [0, 1, 0, 1, 0, 1, 0, 1]  # Replace with your actual true labels
predicted_labels = [0, 1, 0, 1, 0, 0, 1, 1]  # Replace with your actual predicted labels
categories = ['Category A', 'Category B']  # Replace with your actual categories

# Compute confusion matrix
cm = confusion_matrix(true_labels, predicted_labels)

# Function to plot confusion matrix
def plot_confusion_matrix(cm, classes, title='Confusion matrix', cmap=plt.cm.Blues):
    plt.figure(figsize=(8, 6))
    plt.imshow(cm, interpolation='nearest', cmap=cmap)
    plt.title(title)
    plt.colorbar()
    tick_marks = np.arange(len(classes))
    plt.xticks(tick_marks, classes, rotation=45)
    plt.yticks(tick_marks, classes)

    fmt = 'd'
    thresh = cm.max() / 2.
    for i, j in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
        plt.text(j, i, format(cm[i, j], fmt),
                 horizontalalignment="center",
                 color="white" if cm[i, j] > thresh else "black")

    plt.ylabel('True label')
    plt.xlabel('Predicted label')
    plt.tight_layout()

# Plotting confusion matrices for each category
for idx, category in enumerate(categories):
    plot_confusion_matrix(cm, classes=['0', '1'], title=f'Confusion Matrix for {category}')

plt.show()
