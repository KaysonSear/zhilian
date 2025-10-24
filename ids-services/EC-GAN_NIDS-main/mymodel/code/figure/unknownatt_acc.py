import matplotlib.pyplot as plt
import pandas as pd

# Example data (replace with your actual experimental results)
categories = ['BENIGN', 'PortScan', 'Unknown_attack']
precision = [98, 96, 95]
recall = [96, 95, 96]
f1_score = [97, 94, 96]

# Convert data to a DataFrame
df = pd.DataFrame({
    'Category': categories,
    'Precision': precision,
    'Recall': recall,
    'F1-Score': f1_score
})

# Plotting
plt.figure(figsize=(10, 6))

# Plot precision
plt.plot(df['Category'], df['Precision'], marker='o', label='Precision')

# Plot recall
plt.plot(df['Category'], df['Recall'], marker='s', label='Recall')

# Plot F1-score
plt.plot(df['Category'], df['F1-Score'], marker='^', label='F1-Score')

# Add labels and title
plt.xlabel('Categories')
plt.ylabel('Score (%)')  # Adjusted to show percentage
plt.title('Experimental Results')
plt.xticks(rotation=45)
plt.grid(True)
plt.legend()

# Set y-axis limit from 1 to 100
plt.ylim(1, 100)

# Show plot
plt.tight_layout()
plt.show()
