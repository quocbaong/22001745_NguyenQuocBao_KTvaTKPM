module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#FF385C',
          dark: '#E31C5F',
          light: '#FFF1F3',
        },
        secondary: '#1A1A1A',
        accent: '#FFB800',
        background: '#FAFAFA',
        surface: '#FFFFFF',
      },
      fontFamily: {
        sans: ['Plus Jakarta Sans', 'sans-serif'],
      },
      borderRadius: {
        '3xl': '1.5rem',
        '4xl': '2rem',
        '5xl': '2.5rem',
      },
      boxShadow: {
        'soft': '0 2px 4px rgba(0,0,0,0.04), 0 4px 12px rgba(0,0,0,0.08)',
        'premium': '0 12px 24px rgba(0,0,0,0.12), 0 4px 8px rgba(0,0,0,0.04)',
      },
    },
  },
  plugins: [],
}
