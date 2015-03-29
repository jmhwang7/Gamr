<?php
// List of supported API calls.
// Each item corresponds with a file in the functions/ folder, containing a function accepting the specified parameters.
$functions = array(
    'match' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'use_location' => array(
                'required' => false,
                'default' => false,
                'type' => 'boolean',
            ),
            'use_games' => array(
                'required' => false,
                'default' => false,
                'type' => 'boolean',
            ),
            'count' => array(
                'required' => false,
                'default' => 10,
                'type' => 'int'
            )
        )
    ),
    'match_response' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'other_user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'matched' => array(
                'required' => true,
                'type' => 'boolean',
            )
        )
    ),
    'get_messages' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'other_user_id' => array(
                'required' => false,
                'default' => null,
                'type' => 'uuid',
            ),
            'before' => array(
                'required' => false,
                'type' => 'int',
                'default' => null
            )
        )
    ),
    'send_message' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'other_user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'text' => array(
                'required' => true,
                'type' => 'string',
            )
        )
    ),
    'update_location' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'lat' => array(
                'required' => true,
                'type' => 'decimal',
            ),
            'lon' => array(
                'required' => true,
                'type' => 'decimal',
            )
        )
    ),
    'get_profile' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
        )
    ),
    'update_profile' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'username' => array(
                'required' => false,
                'default' => null,
                'type' => 'string',
            ),
            'game' => array(
                'required' => false,
                'default' => null,
                'type' => 'int',
            ),
            'in_game_name' => array(
                'required' => false,
                'default' => null,
                'type' => 'string',
            ),
        )
    ),
    'update_game_field' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'game' => array(
                'required' => true,
                'type' => 'int'
            ),
            'field' => array(
                'required' => true,
                'type' => 'int'
            ),
            'value' => array(
                'required' => true,
                'type' => 'any'
            )
        )
    )
);

?>