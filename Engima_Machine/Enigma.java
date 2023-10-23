def denigma():
    # initialize enigma machine settings
    phrase = input('what is the encrypted message? ')
    key = input('what do you want the answer to be?')
    swap_number_left = 1
    swap_number_middle = 1
    swap_number_right = 1
    three = 'cjgdpshkturawzxfmynqobvlie'
    two = 'slvgbtfxjqohewirzyamkpcndu'
    one = 'lpgszmhaeoqkvxrfybutnicjdw'
    x = 1
    y = 1
    z = 1
    n = 1
    while n == 1:
        alphabet = 'qwertzuioasdfghjkpyxcvbnml'
        alphabet_left = 'qwertzuioasdfghjkpyxcvbnml'
        alphabet_middle = 'qwertzuioasdfghjkpyxcvbnml'
        alphabet_right = 'qwertzuioasdfghjkpyxcvbnml'
        reflector = 'imetcgfraysqbzxwlhkdvupojn'
        pairing = 'mitegcrfyaqszbwxhldkuvopnj'
        message = ''
        if x == 1:
            left = 'lpgszmhaeoqkvxrfybutnicjdw'
        elif x == 2:
            left = 'slvgbtfxjqohewirzyamkpcndu'
        else:
            left = 'cjgdpshkturawzxfmynqobvlie'
        if y == 1:
            middle = 'lpgszmhaeoqkvxrfybutnicjdw'
        elif y == 2:
            middle = 'slvgbtfxjqohewirzyamkpcndu'
        else:
            middle = 'cjgdpshkturawzxfmynqobvlie'
        if z == 1:
            right = 'lpgszmhaeoqkvxrfybutnicjdw'
        elif z == 2:
            right = 'slvgbtfxjqohewirzyamkpcndu'
        else:
            right = 'cjgdpshkturawzxfmynqobvlie'
        right_position = 1
        middle_position = 1
        while swap_number_left != 1:
            left = left[1:] + left[:1]
            alphabet_left = alphabet_left[1:] + alphabet_left[:1]
            swap_number_left -= 1
        while swap_number_middle != 1:
            middle = middle[1:] + middle[:1]
            alphabet_middle = alphabet_middle[1:] + alphabet_middle[:1]
            swap_number_middle -= 1
        while swap_number_right != 1:
            right = right[1:] + right[:1]
            alphabet_left = alphabet_left[1:] + alphabet_left[:1]
            swap_number_right -= 1
        for letter in phrase:
            position = alphabet.find(letter)
            letter = alphabet_right[position]
            position = right.find(letter)
            letter = alphabet_middle[position]
            position = middle.find(letter)
            letter = alphabet_left[position]
            position = left.find(letter)
            letter = reflector[position]
            position = pairing.find(letter)
            letter = reflector[position]
            position = reflector.find(letter)
            letter = left[position]
            position = alphabet_left.find(letter)
            letter = middle[position]
            position = alphabet_middle.find(letter)
            letter = right[position]
            position = alphabet_right.find(letter)
            letter = alphabet[position]
            message = message + letter
            if right_position <= 26:
                right_position += 1
                right = right[-1] + right[:-1]
                alphabet_right = alphabet_right[-1] + alphabet_right[:-1]
            else:
                right_position = 1
                middle = middle[-1] + middle[:-1]
                alphabet_middle = alphabet_middle[-1] + alphabet_middle[:-1]
                right = right[-1] + right[:-1]
                alphabet_right = alphabet_right[-1] + alphabet_right[:-1]
                middle_position += 1
            if middle_position <= 26:
                continue
            else:
                middle_position = 1
                left = left[-1] + left[:-1]
                alphabet_left = alphabet_left[-1] + alphabet_left[:-1]
                middle = middle[-1] + middle[:-1]
                alphabet_middle = alphabet_middle[-1] + alphabet_middle[:-1]
                right = right[-1] + right[:-1]
                alphabet_right = alphabet_right[-1] + alphabet_right[:-1]
        if message == key:
            print(phrase)
            print(x)
            print(y)
            print(z)
            quit()
        if swap_number_right <= 26:
            swap_number_right += 1
        else:
            swap_number_right = 1
            swap_number_middle += 1
        if swap_number_middle > 26:
            swap_number_middle = 1
            swap_number_left += 1
        if swap_number_left > 26:
            swap_number_right = 1
            swap_number_middle = 1
            swap_number_left = 1
            if x <= 3:
                x += 1
            else:
                x = 1
                y += 1
            if y > 3:
                y = 1
                z += 1
            if z > 3:
                n = -1
    print(n)


denigma()