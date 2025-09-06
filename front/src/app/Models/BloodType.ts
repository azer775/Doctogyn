export const BloodType = {
  A_POSITIVE: { id: 1, name: 'A positive' },
  A_NEGATIVE: { id: 2, name: 'A negative' },
  B_POSITIVE: { id: 3, name: 'B positive' },
  B_NEGATIVE: { id: 4, name: 'B negative' },
  AB_POSITIVE: { id: 5, name: 'AB positive' },
  AB_NEGATIVE: { id: 6, name: 'AB negative' },
  O_POSITIVE: { id: 7, name: 'O positive' },
  O_NEGATIVE: { id: 8, name: 'O negative' }
} as const;

// Utility to get BloodType by ID
export function getBloodTypeById(id: number): typeof BloodType[keyof typeof BloodType] | undefined {
  return Object.values(BloodType).find(type => type.id === id);
}