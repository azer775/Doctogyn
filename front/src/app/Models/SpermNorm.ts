export const SpermNorm = {
  DAVID: { value: 'DAVID', name: 'David' },
  KRUGER: { value: 'KRUGER', name: 'Kruger' }
} as const;

// Utility to get SpermNorm by value
export function getSpermNormByValue(value: string): typeof SpermNorm[keyof typeof SpermNorm] | undefined {
  return Object.values(SpermNorm).find(norm => norm.value === value);
}